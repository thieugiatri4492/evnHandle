
'use client';
import { useEffect, useState, useRef } from "react";
import { useRouter } from 'next/navigation';
import Post from "@/app/ui/post/post-interface";
import { Box, Card, CircularProgress, Typography } from "@mui/material";
import { PostObject, PostResponse } from "@/app/lib/definitions";
import { useAppContext } from "@/app/app-provider";

export default function Page() {
    const [posts, setPosts] = useState<PostObject[]>([]);
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const observer = useRef<IntersectionObserver | null>(null);
    const lastPostElementRef = useRef<HTMLDivElement | null>(null);
    const { accessToken } = useAppContext();
    const navigate = useRouter();
    const isFetching = useRef(false); // Ref to prevent duplicate API calls

    // Ensure that loadPosts is only called once when page changes.
    useEffect(() => {
        if (!accessToken) {
            navigate.push("/login");
        } else if (page === 1 && posts.length === 0) {
            loadPosts(1); // Initial load for page 1.
        }
    }, [accessToken, navigate]);

    const loadPosts = async (page: number) => {
        if (loading || isFetching.current) return; // Prevent duplicate calls
        isFetching.current = true;
        setLoading(true);

        try {
            const res = await fetch(`http://localhost:3000/api/post?page=${page}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}`,
                },
                credentials: 'include',
            });

            const data = await res.json();
            if (!res.ok) {
                throw new Error('Failed to fetch posts');
            }

            const response: PostResponse = data;

            // Filter out duplicate posts
            const newPosts = response.result.data.filter(
                (post) => !posts.some((existingPost) => existingPost.id === post.id)
            );

            setPosts((prevPosts) => [...prevPosts, ...newPosts]);
            setTotalPages(response.result.totalPages);
            setHasMore(newPosts.length > 0);
        } catch (error) {
            console.log('Error fetching posts:', error);
            navigate.push("/login");
        } finally {
            setLoading(false);
            isFetching.current = false; // Reset fetch status
        }
    };

    // Handle infinite scroll/pagination
    useEffect(() => {
        if (!hasMore || loading) return;

        if (observer.current) observer.current.disconnect();

        observer.current = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && page < totalPages) {
                setPage((prevPage) => prevPage + 1); // Increment page when scrolling
            }
        });

        if (lastPostElementRef.current) {
            observer.current.observe(lastPostElementRef.current);
        }

        return () => observer.current?.disconnect();
    }, [hasMore, totalPages, loading]);

    // Load more posts when page changes
    useEffect(() => {
        if (page > 1 && accessToken) {
            loadPosts(page);
        }
    }, [page, accessToken]);

    return (
        <>
            <Card
                sx={{
                    minWidth: 500,
                    maxWidth: 600,
                    boxShadow: 3,
                    borderRadius: 2,
                    padding: "20px",
                }}
            >
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "flex-start",
                        width: "100%",
                        gap: "10px",
                    }}
                >
                    <Typography
                        sx={{
                            fontSize: 18,
                            mb: "10px",
                        }}
                    >
                        Your posts,
                    </Typography>
                    {posts.map((post, index) => {
                        if (posts.length === index + 1) {
                            return (
                                <div ref={lastPostElementRef} key={post.id}>
                                    <Post avatarUrl="" post={post} />
                                </div>
                            );
                        } else {
                            return <Post avatarUrl="" key={post.id} post={post} />;
                        }
                    })}
                    {loading && (
                        <Box
                            sx={{ display: "flex", justifyContent: "center", width: "100%" }}
                        >
                            <CircularProgress size="24px" />
                        </Box>
                    )}
                </Box>
            </Card>
        </>
    );
}
