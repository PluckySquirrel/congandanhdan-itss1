export default function getAction(action) {
  switch (action) {
    case 'TRANSLATION':
      return '翻訳';
    case 'INTENT_EXPRESSION':
      return '意図の表現';
    case 'EASY_JAPANESE_MODE':
      return '簡単な日本語モード';
    default:
      return '';
  }
}