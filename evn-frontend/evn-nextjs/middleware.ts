import NextAuth from 'next-auth';
import { authConfig } from './auth.config';
import { NextRequest, NextResponse } from 'next/server';
<<<<<<< HEAD
<<<<<<< HEAD
=======
import { cookies } from 'next/headers'
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
import { cookies } from 'next/headers'
>>>>>>> parent of 50beb3e (Modify UI of evn)

export default NextAuth(authConfig).auth;
// 1. Specify protected and public routes
const publicRoutes = ['/login', '/signup', '/']
export async function middleware(req: NextRequest) {
    // 2. Check if the current route is protected or public
<<<<<<< HEAD
<<<<<<< HEAD
    const path = req.nextUrl.pathname;
    const isPublicRoute = publicRoutes.includes(path);
    const accessToken = req.cookies.get('accessToken')?.value;
    if (!isPublicRoute && !accessToken) {
        //console.log("Redirect to login because miss Accesstoken:", accessToken);
        return NextResponse.redirect(new URL('/login', req.nextUrl));
    }
    if (isPublicRoute && accessToken && !req.nextUrl.pathname.startsWith('/dashboard')) {
        console.log("Redirect to dasboard because have access token and path name:", accessToken, req.nextUrl.pathname);
        return NextResponse.redirect(new URL('/dashboard', req.nextUrl));
    }

    return NextResponse.next();
=======
    const path = req.nextUrl.pathname
    const isPublicRoute = publicRoutes.includes(path)
    const token = cookies().get('accessToken')?.value
    if (!isPublicRoute && !token) {
        return NextResponse.redirect(new URL('/login', req.nextUrl))
    }
    if (req.nextUrl.pathname.startsWith('/api')) {
        const requestHeaders = new Headers(req.headers);
        requestHeaders.set('Authorization', `Bearer ${token}`);
        const modifiedRequest = new Request(req, {
            headers: requestHeaders,
        });

        return NextResponse.next({
            request: modifiedRequest,
        });
    }
    if (isPublicRoute && token && !req.nextUrl.pathname.startsWith('/dashboard')) {

        return NextResponse.redirect(new URL('/dashboard', req.nextUrl))
    }

    return NextResponse.next()
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
    const path = req.nextUrl.pathname
    const isPublicRoute = publicRoutes.includes(path)
    const token = cookies().get('accessToken')?.value
    if (!isPublicRoute && !token) {
        return NextResponse.redirect(new URL('/login', req.nextUrl))
    }
    if (req.nextUrl.pathname.startsWith('/api')) {
        const requestHeaders = new Headers(req.headers);
        requestHeaders.set('Authorization', `Bearer ${token}`);
        const modifiedRequest = new Request(req, {
            headers: requestHeaders,
        });

        return NextResponse.next({
            request: modifiedRequest,
        });
    }
    if (isPublicRoute && token && !req.nextUrl.pathname.startsWith('/dashboard')) {

        return NextResponse.redirect(new URL('/dashboard', req.nextUrl))
    }

    return NextResponse.next()
>>>>>>> parent of 50beb3e (Modify UI of evn)

}

export const config = {
    // https://nextjs.org/docs/app/building-your-application/routing/middleware#matcher
    matcher: ['/((?!api/system/auth/login|_next/static|_next/image|.*\\.png$).*)'],
};