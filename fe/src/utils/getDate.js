export default function getDate(timestamp) {
  const date = timestamp.substring(0, 10);
  const dateString = new Date(date).toLocaleString("en-us", { day: "2-digit" ,month: "short", year: "numeric" });
  return dateString;
}