import { createContext, ReactNode, useState } from "react";

export const TitleContext = createContext({
  title: "",
  setTitle: (arg: string) => {},
});

type ProviderProps = {
  children: ReactNode;
};

const TitleContextProvider = ({ children }: ProviderProps) => {
  const [title, setTitle] = useState("");

  return (
    <TitleContext.Provider value={{ title, setTitle }}>
      {children}
    </TitleContext.Provider>
  );
};

export default TitleContextProvider;
