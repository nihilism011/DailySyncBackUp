type Category = 'FOOD' | 'TRANSPORT' | 'HOUSING' | 'SALARY' | 'CULTURE' | 'MEDICAL' | 'OTHER'
export interface Account {
  id: number
  category: Category
  accountDate: string
  title: string
  description: string
  amount: number
  fixed: boolean
}
