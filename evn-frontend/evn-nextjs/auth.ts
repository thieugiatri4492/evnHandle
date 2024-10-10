import NextAuth from 'next-auth';
import Credentials from 'next-auth/providers/credentials';
import { authConfig } from './auth.config';
import { z } from 'zod';
import { sql } from '@vercel/postgres';
import type { User, UserJavaApi } from '@/app/lib/definitions';
import { cookies } from 'next/headers';
//import bcrypt from 'bcrypt';

<<<<<<< HEAD
<<<<<<< HEAD
//Connect directly to vercel cloud
async function getUserVercel(email: string): Promise<User | undefined> {
=======
async function getUser(email: string): Promise<User | undefined> {
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
async function getUser(email: string): Promise<User | undefined> {
>>>>>>> parent of 50beb3e (Modify UI of evn)
    try {
        const user = await sql<User>`SELECT * FROM users WHERE email=${email}`;
        return user.rows[0];
    } catch (error) {
        console.error('Failed to fetch user:', error);
        throw new Error('Failed to fetch user.');
    }
}
//Use route handler to hide the actual api
async function apiLoginJava(userName: string, password: string): Promise<UserJavaApi | undefined> {
    try {
        return fetch("http://localhost:3000/api/system/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            cache: 'no-store',
            body: JSON.stringify({
                userName: userName,
                password: password,
            }),

        })
            .then((response) => {
<<<<<<< HEAD
<<<<<<< HEAD
                //console.log("This is the result from API login java when call in auth nextjs", response);
=======
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
>>>>>>> parent of 50beb3e (Modify UI of evn)
                return response.json();
            })
            .then((data) => {
                if (data.code !== 200) {
                    throw new Error(data.code + " " + data.message)
                }
                return data as Promise<UserJavaApi>
            });
    } catch (error) {
        console.error('Failed to fetch user:', error);
        throw new Error('Failed to fetch user.');
    }
}
export const { auth, signIn, signOut } = NextAuth({
    ...authConfig,
    providers: [
        Credentials({
            async authorize(credentials) {
                const parsedCredentials = z
                    .object({ userName: z.string(), password: z.string().min(5) })
                    .safeParse(credentials);
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======

>>>>>>> parent of 50beb3e (Modify UI of evn)
                if (parsedCredentials.success) {
                    const { userName, password } = parsedCredentials.data;
                    const user = await apiLoginJava(userName, password);
                    if (!user) return null;
<<<<<<< HEAD
<<<<<<< HEAD
                    cookies().set('accessToken', user.result.token, {
                        httpOnly: true,
                        path: '/',
                        sameSite: 'lax',
                    });
=======
                    cookies().set('accessToken', user.result.token);
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
                    cookies().set('accessToken', user.result.token);
>>>>>>> parent of 50beb3e (Modify UI of evn)
                    return user as any;
                }

                return null;
            },
        }),
    ],
    events: {
        async signOut() {
            cookies().set('accessToken', '', { maxAge: -1, path: '/' });
            console.log('User signed out. Access token cleared.');
        }
    }
});
//For reference
// export const { auth, signIn, signOut } = NextAuth({
//     ...authConfig,
//     providers: [
//         Credentials({
//             async authorize(credentials) {
//                 const parsedCredentials = z
//                     .object({ email: z.string().email(), password: z.string().min(6) })
//                     .safeParse(credentials);

//                 if (parsedCredentials.success) {
//                     const { email, password } = parsedCredentials.data;
//                     const user = await getUser(email);
//                     if (!user) return null;
//                     const passwordsMatch = await bcrypt.compare(password, user.password);
//                     if (passwordsMatch) return user;
//                 }

//                 return null;
//             },
//         }),
//     ],
// });