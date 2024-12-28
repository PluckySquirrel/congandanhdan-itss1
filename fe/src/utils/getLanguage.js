export default function getLanguage(language) {
  console.log(language);
  return language.charAt(0) + language.toLowerCase().substring(1);
}