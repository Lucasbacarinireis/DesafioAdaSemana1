declare module "@mui/material/styles" {
  interface PaletteColor {
    100: string;
    200: string;
    300: string;
    400: string;
    500: string;
    red: string;
    purple: string;
    green: string;
    yellow: string;
  }
}

export const primary = {
  100: "#D3F5FE",
  200: "#A7E7FD",
  300: "#7AD2FA",
  400: "#59BCF5",
  500: "#2499EF",
  main: "#017CEB",
  light: "#D3F5FE",
  dark: "#1A77CD",
  red: "#FF6B93",
  purple: "#A798FF",
  green: "#5be49b",
  yellow: "#FF9777",
};

export const secondary = {
  100: "#F9F9F9",
  200: "#ECEFF5",
  300: "#E5EAF2", // outline or border
  400: "#94A4C4", // text muted
  500: "#1d2438", // main text
  main: "#1d2438", // main text
  light: "#F9F9F9",
  red: "#FF6B93",
  purple: "#A798FF",
  green: "#5be49b",
  yellow: "#FF9777",
};

export const error = {
  main: "#FD396D",
};

export const success = {
  main: "#2CC5BD",
};

export const warning = {
  main: "#FFE91F",
  dark: "#FFD600",
};

export const info = {
  main: "#A798FF",
};

export const text = {
  primary: secondary[500],
  secondary: secondary[400],
  disabled: secondary[300],
};
