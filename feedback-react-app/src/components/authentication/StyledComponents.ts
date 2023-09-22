import { Box, Button, styled, TextField, TextFieldProps } from "@mui/material";

// styled components
export const SocialIconButton = styled(Button)(({ theme }) => ({
  width: "100%",
  height: 48,
  fontSize: 13,
  borderRadius: "6px",
  border: "2px solid",
}));

export const TextFieldWrapper = styled(Box)(({ theme }) => ({
  width: "100%",
  marginTop: "1rem",
}));

export const StyledTextField = styled(TextField)<TextFieldProps>(
  ({ theme }) => ({
    "&:hover, & .MuiOutlinedInput-root:hover": {
      "& .MuiOutlinedInput-notchedOutline": {
        borderColor: theme.palette.primary.main,
      },
    },
  })
);
