import httpClient from "../../configurations/httpClient";
import { API_ROUTE } from "../../configurations/configurations";
<<<<<<< HEAD
<<<<<<< HEAD
import { AxiosResponse } from 'axios';
=======
>>>>>>> parent of 50beb3e (Modify UI of evn)
import { NextRequest, NextResponse } from "next/server";

<<<<<<< HEAD
export async function GET(request: NextRequest) {
    const page = request.nextUrl.searchParams.get('page');
    const cookieStore = cookies();
    const accessToken = cookieStore.get('accessToken');
    //console.log("This is the accesToken get from nextServer", accessToken?.value);
    try {
        const response: AxiosResponse = await httpClient.get(API_ROUTE.MY_POST, {
            headers: {
                Authorization: `Bearer ${accessToken?.value}`,
            },
            params: {
                page: page,
                size: 10,
            }
        });
        if (response && response.data) {
            const result = new NextResponse(JSON.stringify(response.data));
            return result;
        } else {
            return new NextResponse(JSON.stringify({ message: 'No data found' }), { status: 404 });
        }
    } catch (error: any) {
        if (error.response) {
            console.log("Error data", error.response.data);
            console.log("Error status", error.response.status);
            console.log("Error header", error.response.headers);
        } else {
            console.log('Error something else:', error.message);
        }

        return new NextResponse(JSON.stringify({ error: 'Failed to fetch posts' }), { status: 500 });
    }
}
=======
import { NextRequest, NextResponse } from "next/server";

export async function GET() {

}
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
export async function GET() {

}
>>>>>>> parent of 50beb3e (Modify UI of evn)