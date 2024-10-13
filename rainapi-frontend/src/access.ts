/**
 * @see https://umijs.org/docs/max/access#access
 * */
export default function access(initialState: InitialState | undefined) {
  const { loginUser } = initialState ?? {};
  return {
    canUser: loginUser,
    // if loginUser exists, and owns admin role
    canAdmin: loginUser?.userRole === 'admin',
  };
}
