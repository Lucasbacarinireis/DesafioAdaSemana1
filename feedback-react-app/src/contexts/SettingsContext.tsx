import { createContext, ReactNode } from "react";

import useLocalStorage from "hooks/useLocalStorage";

import { THEMES } from "../constants";
import { themeSettingsProps } from "../theme";

const initialSettings: themeSettingsProps = {
  direction: "ltr",
  theme: THEMES.LIGHT,
  responsiveFontSizes: true,
};

export const SettingsContext = createContext({
  settings: initialSettings,
  saveSettings: (arg: themeSettingsProps) => {},
});

type settingsProviderProps = {
  children: ReactNode;
};

const SettingsProvider = ({ children }: settingsProviderProps) => {
  const { data: settings, storeData: setSettings } = useLocalStorage(
    "settings",
    initialSettings
  );

  const saveSettings = (updateSettings: themeSettingsProps) => {
    setSettings(updateSettings);
  };

  return (
    <SettingsContext.Provider value={{ settings, saveSettings }}>
      {children}
    </SettingsContext.Provider>
  );
};

export default SettingsProvider;
