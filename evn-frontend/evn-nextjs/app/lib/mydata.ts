import { Revenue } from './definitions';

export async function fetchRevenue(): Promise<Revenue[]> {
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