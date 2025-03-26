export interface ApplicationStatus {
  id: number; // Removed the question mark to make it required
  status?: string;
  description?: string;
}
