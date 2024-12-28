export default function getLanguageFromTag(tag) {
    switch (tag) {
      case 'vi':
        return 'VIETNAMESE';
      case 'en':
        return 'ENGLISH';
      default:
        return 'JAPANESE';
    }
  }