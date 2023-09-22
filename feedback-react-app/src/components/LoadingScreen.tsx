import { useEffect } from "react";

import { Box, CircularProgress } from "@mui/material";
import NProgress from "nprogress";

const LoadingScreen = () => {
  NProgress.configure({
    showSpinner: false,
  });

  useEffect(() => {
    NProgress.start();

    return () => {
      NProgress.done();
    };
  }, []);

  return (
    <>
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          height: "100vh",
        }}
      >
        <CircularProgress />
      </Box>
    </>
  );
};

export default LoadingScreen;
