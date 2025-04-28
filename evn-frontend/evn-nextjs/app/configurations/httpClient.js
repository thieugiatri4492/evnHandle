import axios from "axios";
import { CONFIG } from "./configurations";

const httpClient = axios.create({
    baseURL: CONFIG.API_GATEWAY,
    timeout: 30000,
    headers: {
        "Content-Type": "application/json",
    },
});

export default httpClient;