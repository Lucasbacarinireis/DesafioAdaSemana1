import { Fragment, ReactNode } from "react";
//import useAuth from "hooks/useAuth";
//import { Navigate } from "react-router-dom";

// component props interface
interface GuestGuardProps {
  children: ReactNode;
}
const GuestGuard = ({ children }: GuestGuardProps) => {
  ////Verifica se o usuário está logado e redireciona para /dashboard, impedindo que volte para paginas de autenticação

  //   const { isAuthenticated } = useAuth();

  //   if (isAuthenticated) {
  //     return <Navigate to="/dashboard" />;
  //   }

  return <Fragment>{children}</Fragment>;
};

export default GuestGuard;
