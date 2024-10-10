// lib/db.js
import { createClient } from '@vercel/postgres';
import dotenv from 'dotenv';

dotenv.config();

export const query = async (queryText, params) => {
  const client = createClient({
    connectionString: process.env.POSTGRES_URL,
  });

  await client.connect();

  try {
    const res = await client.query(queryText, params);
    return res;
  } finally {
    await client.end();
  }
};
