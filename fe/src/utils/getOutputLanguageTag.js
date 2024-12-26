export default function getOutputLanguageTag(language) {
  switch (language) {
    case 'VIETNAMESE':
      return 'vi';
    case 'ENGLISH':
      return 'en';
    default:
      return 'ja';
  }
}