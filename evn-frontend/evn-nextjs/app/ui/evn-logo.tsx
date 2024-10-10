import { lusitana } from '@/app/ui/fonts';
import Image from 'next/image';

export default function EvnLogo() {
  return (
    <div className={`${lusitana.className} flex flex-row items-center leading-none text-white`}>
      <Image src="/evn-logo.png" alt="EVN Logo" width={48} height={48} />
      <p className="text-[44px] text-blue-800">EVN</p>
    </div>
  );
}