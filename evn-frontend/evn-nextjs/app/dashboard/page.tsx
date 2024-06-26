import { lusitana } from '@/app/ui/fonts';
import RevenueChart  from '@/app/ui/dashboard/revenue-chart';
import { Revenue } from '@/app/lib/definitions';

async function fetchRevenue():  Promise<Revenue[]> {
  const response = await fetch(`http://localhost:3000/api/revenue`,{ cache: 'no-store'});
  const data = await response.json();
  const resultArray = data.data.result;
  // Ensure data conforms to the Revenue[] type
  const revenueData: Revenue[] = resultArray.map((item: any) => ({
    month: item.revenueMonth, // Adjust this based on the actual structure of each item
    revenue: item.revenueValue, // Adjust this based on the actual structure of each item
  }));
  return revenueData;
}

export default async function Page() {
    const revenue = await fetchRevenue();
    return (
    <main>
      <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
        Dashboard
      </h1>
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
        {<RevenueChart revenue={revenue}  />}
      </div>
      <div className="mt-6 grid grid-cols-1 gap-6 md:grid-cols-4 lg:grid-cols-8">
        This is a table
      </div>
    </main>
    );  
  }