export interface Loan {
  id: number;
  principal_balance: number;
  interest: number;
  term_length: number;
  total_balance: number;
  applicationStatus: { application_statuses_id: number };
  loanType: { loan_type_id: number };
  userProfile: { user_profiles_id: number };
}
