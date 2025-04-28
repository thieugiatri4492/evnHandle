import httpClient from "../../../../configurations/httpClient";
import { API_ROUTE } from "../../../../configurations/configurations";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
    const loginInfo = await request.json();
    const loginResp = {
        userName: loginInfo.userName,
        password: loginInfo.password
    }
    const response = await httpClient.post(API_ROUTE.LOGIN, loginResp);
    if (!response.data.result.token) {
        return new NextResponse(JSON.stringify(response.data.result));
    }
    const result = new NextResponse(JSON.stringify(response.data), {
        headers: {
            'Set-Cookie': `accessToken=${response.data.result.token}; Path=/; HttpOnly`
        }
    });
    //console.log("This is the result from API login java", result);
    return result;
}