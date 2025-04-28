import '@/app/ui/global.css';
import { inter } from '@/app/ui/fonts';
import { Metadata } from 'next';
import AppProvider from './app-provider';
import { cookies } from 'next/headers';

export const metadata: Metadata = {
  title: {
    template: '%s | EVN Handle',
    default: 'EVN Handle'
  },
  description: "A system handle all problem of EVN.",
  metadataBase: new URL('https://next-learn-dashboard.vercel.sh'),
};

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const cookiesStore = cookies();
  const accessToken = await cookiesStore.get("accessToken"); // Await `cookiesStore.get`

  return (
    <html lang="en">
      <body className={`${inter.className} antialiased`}>
        <AppProvider initialAccessToken={accessToken?.value}>
          {children}
        </AppProvider>
      </body>
    </html>
  );
}
