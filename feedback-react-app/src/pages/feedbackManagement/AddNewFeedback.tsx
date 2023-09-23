import {
  Box,
  Button,
  Card,
  FormControl,
  FormHelperText,
  Grid,
  MenuItem,
  Select,
} from "@mui/material";
import InputLabel from "@mui/material/InputLabel";

import { LoadingButton } from "@mui/lab";

import {useNavigate } from "react-router-dom";
import LightTextField from "components/LightTextField";

import { useFormik } from "formik";
import useTitle from "hooks/useTitle";

import { FC, useState } from "react";
import toast from "react-hot-toast";

import api from 'service/Api';

import * as Yup from "yup";

const AddNewUser: FC = () => {
  // change navbar title
  useTitle("Adicionar Novo Feedback");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  let navigate = useNavigate();


  const initialValues = {
    type: "",
    message: "",
    status:"RECEBIDO",
  }

  const validationSchema = Yup.object().shape({
    type: Yup.string().required("Campo obrigatório"),
    message: Yup.string().required("Campo obrigatório"),
  });

  const { values, errors, handleChange, handleSubmit, touched } = useFormik({
    initialValues,
    validationSchema,
    onSubmit: async (values: any, { resetForm }) => {
      setLoading(true);
      api.post('/enviar-feedback', values)
      .then((response) => {
        setLoading(false);
        toast.success(`${response.data}`);
        resetForm();
      })
      .catch((error) => {
        setLoading(false);
        toast.error(`${error}`);
        setError(error); 
      });
    }
  });


  return (
    <Box pt={2} pb={4}>
      <Card sx={{ padding: 4 }}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <form onSubmit={handleSubmit} >
              <Grid item xs={12}>
                <FormControl
                  sx={{
                    my: 1,
                    width: "100%",
                    "& .MuiOutlinedInput-root textarea": { padding: 0 },
                  }}
                >
                  <InputLabel>Tipo</InputLabel>
                  <Select
                    name="type"
                    value={values.type}
                    onChange={handleChange}
                    label="Tipo"
                  >
                    <MenuItem value={"SUGESTAO"}>Sugestão</MenuItem>
                    <MenuItem value={"ELOGIO"}>Elogio</MenuItem>
                    <MenuItem value={"CRITICA"}>Crítica</MenuItem>
                  </Select>
                </FormControl>
              </Grid>

              <Grid item xs={12}>
                <LightTextField
                  multiline
                  fullWidth
                  rows={10}
                  name="message"
                  placeholder="Escreva aqui seu feedback"
                  value={values.message}
                  onChange={handleChange}
                  error={Boolean(touched.message && errors.message)}
                  helperText={touched.message && errors.message}
                  sx={{
                    "& .MuiOutlinedInput-root textarea": { padding: 0 },
                  }}
                />
              </Grid>

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

              <Grid container justifyContent="flex-end">

                <Grid item md={1} sx={{ mt: 4 , mx:2}}>
                    <Button fullWidth type="reset" onClick={() => navigate("/dashboard")} variant="outlined">
                      Cancelar
                    </Button>
                </Grid>

                <Grid item md={1} sx={{ mt: 4 }}>
                  {loading ? (
                    <LoadingButton loading fullWidth variant="contained">
                      Enviar
                    </LoadingButton>
                  ) : (
                    <Button fullWidth type="submit" variant="contained">
                      Enviar
                    </Button>
                  )}
                </Grid>
              </Grid>
            </form>
          </Grid>
        </Grid>
      </Card>
    </Box>
  );
};

export default AddNewUser;
