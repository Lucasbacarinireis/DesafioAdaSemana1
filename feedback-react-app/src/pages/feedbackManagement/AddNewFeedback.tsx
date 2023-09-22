import {
  Box,
  Button,
  Card,
  FormControl,
  Grid,
  MenuItem,
  Select,
} from "@mui/material";
import InputLabel from "@mui/material/InputLabel";

import { LoadingButton } from "@mui/lab";

import LightTextField from "components/LightTextField";
import { useFormik } from "formik";
import useTitle from "hooks/useTitle";
import { FC, useState } from "react";
import * as Yup from "yup";

const AddNewUser: FC = () => {
  // change navbar title
  useTitle("Adicionar Novo Feedback");

  const [loading, setLoading] = useState(false);

  const initialValues = {
    type: "",
    feedback: "",
  };

  const validationSchema = Yup.object().shape({
    type: Yup.string().required("type is Required!"),
    feedback: Yup.string().required("feedback is Required!"),
  });

  const { values, errors, handleChange, handleSubmit, touched } = useFormik({
    initialValues,
    validationSchema,
    onSubmit: () => {},
  });

  return (
    <Box pt={2} pb={4}>
      <Card sx={{ padding: 4 }}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <form onSubmit={handleSubmit}>
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
                    <MenuItem value={""}></MenuItem>
                    <MenuItem value={"Sugestão"}>Sugestão</MenuItem>
                    <MenuItem value={"Elogio"}>Elogio</MenuItem>
                    <MenuItem value={"Crítica"}>Crítica</MenuItem>
                  </Select>
                </FormControl>
              </Grid>

              <Grid item xs={12}>
                <LightTextField
                  multiline
                  fullWidth
                  rows={10}
                  name="feedback"
                  placeholder="Escreva aqui seu feedback"
                  value={values.feedback}
                  onChange={handleChange}
                  error={Boolean(touched.feedback && errors.feedback)}
                  helperText={touched.feedback && errors.feedback}
                  sx={{
                    "& .MuiOutlinedInput-root textarea": { padding: 0 },
                  }}
                />
              </Grid>
              <Grid container justifyContent="flex-end">
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
