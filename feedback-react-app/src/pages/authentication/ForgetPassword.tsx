import { FC, useState } from "react";

import toast from "react-hot-toast";
import { Link, useNavigate } from "react-router-dom";

import { LoadingButton } from "@mui/lab";
import { Box, Button, Card, FormHelperText } from "@mui/material";

import FlexBox from "components/FlexBox";
import LightTextField from "components/LightTextField";
import { H1, Small } from "components/Typography";

import { useFormik } from "formik";
import * as Yup from "yup";

const ForgetPassword: FC = () => {
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  let navigate = useNavigate();

  const initialValues = {
    email: "teste@teste.com",
    submit: null,
  };

  //validação do formulário com a lib Yup:
  const validationSchema = Yup.object().shape({
    email: Yup.string()
      .email("Preenche com um e-mail válido!")
      .max(255)
      .required("Email é obrigatório"),
  });

  const { errors, values, touched, handleBlur, handleChange, handleSubmit } =
    useFormik({
      initialValues,
      validationSchema,
      onSubmit: (values) => {
        setLoading(true);

        setTimeout(() => {
          setLoading(false);
          toast.success(
            "Instruções para redefinição enviada para e-mail informado!"
          );
          navigate("/dashboard");
        }, 2800);

        if (error) {
          setError("Error!");
          setLoading(false);
        }
      },
    });

  return (
    <FlexBox
      height="100vh"
      alignItems="center"
      flexDirection="column"
      justifyContent="center"
    >
      <Card sx={{ padding: 4, maxWidth: 400, marginTop: 4, boxShadow: 1 }}>
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
          <H1 color="primary.main" fontSize={20} fontWeight={700}>
            Redefinir senha
          </H1>
        </FlexBox>

        <FlexBox justifyContent="space-between" flexWrap="wrap" my={2}>
          <form noValidate onSubmit={handleSubmit} style={{ width: "100%" }}>
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
                  enviar
                </LoadingButton>
              ) : (
                <Button fullWidth type="submit" variant="contained">
                  enviar
                </Button>
              )}
            </Box>
          </form>

          <Small margin="auto" mt={3} color="text.disabled">
            Primeiro acesso?{" "}
            <Link to="/register">
              <Small color="primary.main">Cadastre-se</Small>
            </Link>
          </Small>
        </FlexBox>
      </Card>
    </FlexBox>
  );
};

export default ForgetPassword;
