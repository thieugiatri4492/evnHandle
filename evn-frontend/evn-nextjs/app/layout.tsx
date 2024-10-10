import '@/app/ui/global.css';
import { inter } from '@/app/ui/fonts';
import { Metadata } from 'next';
<<<<<<< HEAD
<<<<<<< HEAD
import AppProvider from './app-provider';
import { cookies } from 'next/headers';
=======
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
>>>>>>> parent of 50beb3e (Modify UI of evn)

export const metadata: Metadata = {
  title: {
    template: '%s | EVN Handle',
    default: 'EVN Handle'
  },
  description: "A system handle all problem of EVN.",
  metadataBase: new URL('https://next-learn-dashboard.vercel.sh'),
};
export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
<<<<<<< HEAD
<<<<<<< HEAD
  const cookiesStore = cookies();
  const accessToken = cookiesStore.get("accessToken");
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>
        <AppProvider initialAccessToken={accessToken?.value}>
          {children}
        </AppProvider>
      </body>
=======
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>{children}</body>
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>{children}</body>
>>>>>>> parent of 50beb3e (Modify UI of evn)
    </html>
  );
}
