export default function getLanguage(language) {
  return language.charAt(0) + language.toLowerCase().substring(1);
}