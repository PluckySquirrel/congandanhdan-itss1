export default function getAction(action) {
  switch (action) {
    case 'TRANSLATION':
      return 'Translation';
    case 'INTENT_EXPRESSION':
      return 'Intent expression';
    case 'EASY_JAPANESE_MODE':
      return 'Easy Japanese';
    default:
      return '';
  }
}