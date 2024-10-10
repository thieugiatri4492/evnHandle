import axios from "axios";
import { CONFIG } from "./configurations";

const httpClient = axios.create({
    baseURL: CONFIG.API_GATEWAY,
    timeout: 30000,
    headers: {
        "Content-Type": "application/json",
    },
<<<<<<< HEAD
<<<<<<< HEAD
    cache: 'no-store'
=======
>>>>>>> 51314af341f726437770efaef38774b54cde97b6
=======
>>>>>>> parent of 50beb3e (Modify UI of evn)
});

export default httpClient;