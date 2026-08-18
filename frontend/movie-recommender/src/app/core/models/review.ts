export interface ReviewDto {
  id: number;
  comment: string;
  rating: number; // 1-10
  userName: string;
  userId: number;
  createdAt: string; // ISO datetime
}