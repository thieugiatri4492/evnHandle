export async function GET () {
    const res = await fetch(`http://localhost:8081/profile/revenue`, { 
      headers: {
        'Content-Type': 'application/json'
      }
    ,
      cache: 'no-store'
    })
    if (!res.ok) {
      return new Response('Network response was not ok', { status: res.status });
    }
    const data = await res.json();
    const result = Response.json({ data }); 
    return result;
  }