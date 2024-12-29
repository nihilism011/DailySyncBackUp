import js from '@eslint/js'
import pluginVue from 'eslint-plugin-vue'
import skipFormatting from '@vue/eslint-config-prettier/skip-formatting'

export default [
  {
    name: 'app/files-to-lint',
    files: ['**/*.{js,mjs,jsx,vue,ts,tsx}'],
  },

  {
    name: 'app/files-to-ignore',
    ignores: ['**/dist/**', '**/dist-ssr/**', '**/coverage/**'],
  },

  js.configs.recommended,
  ...pluginVue.configs['flat/essential'],
  skipFormatting,
  {
    parser: '@typescript-eslint/parser', // TypeScript 파서 설정
    parserOptions: {
      ecmaVersion: 2020, // 최신 JavaScript 버전 사용
      sourceType: 'module', // ES 모듈 사용
      project: './tsconfig.json', // tsconfig.json 파일을 참조하도록 설정
    },
    extends: [
      'eslint:recommended', // ESLint 기본 규칙 사용
      'plugin:@typescript-eslint/recommended', // TypeScript 권장 규칙 추가
      'plugin:vue/vue3-recommended', // Vue 3 권장 규칙 추가
    ],
    plugins: ['@typescript-eslint'], // TypeScript 플러그인 활성화
    rules: {
      // 여기에서 추가 규칙을 설정할 수 있습니다
    },
  },
]
