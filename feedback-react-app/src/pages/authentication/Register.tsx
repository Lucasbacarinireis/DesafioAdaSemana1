import { LoadingButton } from "@mui/lab";
import {
  Box,
  Button,
  Card,
  Checkbox,
  FormControlLabel,
  FormHelperText,
} from "@mui/material";
import { TextFieldWrapper } from "components/authentication/StyledComponents";
import FlexBox from "components/FlexBox";
import LightTextField from "components/LightTextField";
import { H1, Small } from "components/Typography";
import { useFormik } from "formik";
import useAuth from "hooks/useAuth";
import { FC, useState } from "react";
import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";
import * as Yup from "yup";

const Register: FC = () => {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const initialValues = {
    name: "",
    email: "",
    password: "",
    terms: true,
    submit: null,
  };

  //validação do formulário com Yup:
  const validationSchema = Yup.object().shape({
    name: Yup.string().max(50).required("Campo obrigatório"),
    email: Yup.string()
      .email("Preenche com um e-mail válido!")
      .max(255)
      .required("Campo obrigatório"),
    password: Yup.string()
      .min(6, "A senha deve ter no mínimo 6 caracteres")
      .required("Campo obrigatória"),
  });

  const { errors, values, touched, handleBlur, handleChange, handleSubmit } =
    useFormik({
      initialValues,
      validationSchema,
      onSubmit: async (values: any) => {
        setLoading(true);
        try {
          await register(
            values.name,
            values.username,
            values.email,
            values.password
          );
          setLoading(false);
          toast.success("Cadastro feito com sucesso!");
          navigate("/dashboard");
        } catch (error: any) {
          setError(error?.message);
          setLoading(false);
        }
      },
    });

  return (
    <FlexBox
      sx={{
        alignItems: "center",
        flexDirection: "column",
        justifyContent: "center",
        height: { sm: "100%" },
      }}
    >
      <Card sx={{ padding: 4, maxWidth: 400, boxShadow: 1 }}>
        <FlexBox
          alignItems="center"
          flexDirection="column"
          justifyContent="center"
          mb={5}
        >
          <Box mb={1}>
            <img
              src="/static/logo/logo.svg"
              width="180px"
              alt="Cielo Dev Logo"
            />
          </Box>
          <H1 fontSize={20} fontWeight={700}>
            Criar Conta
          </H1>
        </FlexBox>

        <FlexBox justifyContent="space-between" flexWrap="wrap" my="1rem">
          <form noValidate onSubmit={handleSubmit} style={{ width: "100%" }}>
            <FlexBox justifyContent="space-between" flexWrap="wrap">
              <TextFieldWrapper>
                <LightTextField
                  fullWidth
                  name="name"
                  type="text"
                  label="Name"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.name || ""}
                  error={Boolean(touched.name && errors.name)}
                  helperText={touched.name && errors.name}
                />
              </TextFieldWrapper>

              <TextFieldWrapper>
                <LightTextField
                  fullWidth
                  name="email"
                  type="email"
                  label="Email"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.email || ""}
                  error={Boolean(touched.email && errors.email)}
                  helperText={touched.email && errors.email}
                />
              </TextFieldWrapper>
            </FlexBox>

            <TextFieldWrapper sx={{ mt: 2, width: "100%" }}>
              <LightTextField
                fullWidth
                name="password"
                type="password"
                label="Password"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.password || ""}
                error={Boolean(touched.password && errors.password)}
                helperText={touched.password && errors.password}
              />
            </TextFieldWrapper>

            <FormControlLabel
              control={
                <Checkbox
                  disableRipple
                  checked={values.terms}
                  onChange={handleChange}
                  name="terms"
                />
              }
              label="Concordo com os termos e condições"
              sx={{
                marginTop: "0.5rem",
                "& .MuiTypography-root": { fontWeight: 600 },
              }}
            />

            {error && (
              <FormHelperText
                error
                sx={{
                  mt: 2,
                  fontSize: 13,
                  fontWeight: 500,
                  textAlign: "center",
                }}
              >
                {error}
              </FormHelperText>
            )}

            <Box sx={{ mt: 4 }}>
              {loading ? (
                <LoadingButton loading fullWidth variant="contained">
                  Enviar
                </LoadingButton>
              ) : (
                <Button fullWidth type="submit" variant="contained">
                  Enviar
                </Button>
              )}
            </Box>
          </form>

          <Small margin="auto" mt={3} color="text.disabled">
            Você já tem uma conta?
            <Link to="/login">
              <Small color="primary.main"> Fazer login</Small>
            </Link>
          </Small>
        </FlexBox>
      </Card>
    </FlexBox>
  );
};

export default Register;
