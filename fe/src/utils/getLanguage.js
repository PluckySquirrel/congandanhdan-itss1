export default function getLanguage(language) {
  console.log(language);
  // return language.charAt(0) + language.toLowerCase().substring(1);
  switch (language) {
    case "ENGLISH":
      return "英語";
    case "JAPANESE":
      return "日本語";
    case "VIETNAMESE":
      return "ベトナム語";
  }
}