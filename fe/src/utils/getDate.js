export default function getDate(timestamp) {
  // const dateString = new Date(date).toLocaleString("en-us", { day: "2-digit" ,month: "short", year: "numeric" });
  
  const date = new Date(timestamp.substring(0, 10));
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}