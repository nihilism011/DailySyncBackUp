export const categories = {
  FOOD: {
    name: '식비',
    color: '#FF9E80',
  },
  TRANSPORT: {
    name: '교통비',
    color: '#5CA8D3',
  },
  HOUSING: {
    name: '주거비',
    color: '#9EBC3D',
  },
  SALARY: {
    name: '월급',
    color: '#F39C12',
  },
  CULTURE: {
    name: '문화생활',
    color: '#F06292',
  },
  MEDICAL: {
    name: '의료비',
    color: '#4DB6AC',
  },
  OTHER: {
    name: '기타',
    color: '#D1A3E0',
  },
}
export const categoryArray = Object.entries(categories).map(([key, value]) => ({
  ...value,
  key: key.toUpperCase(),
}))
