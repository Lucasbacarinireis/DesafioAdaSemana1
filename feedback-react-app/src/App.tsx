import { FC } from "react";
import { Toaster } from "react-hot-toast";
import { useRoutes } from "react-router-dom";

import { CssBaseline, ThemeProvider } from "@mui/material";
import { StyledEngineProvider } from "@mui/material/styles";

import routes from "./routes";
import { AppTheme } from "./theme";

const App: FC = () => {
  const allPages = useRoutes(routes);

  // App theme
  const appTheme = AppTheme();

  // toaster options
  const toasterOptions = {
    style: {
      fontWeight: 500,
      fontFamily: "'Montserrat', sans-serif",
    },
  };

  return (
    <StyledEngineProvider injectFirst>
      <ThemeProvider theme={appTheme}>
        <CssBaseline />
        <Toaster toastOptions={toasterOptions} />
        {allPages}
      </ThemeProvider>
    </StyledEngineProvider>
  );
};

export default App;
