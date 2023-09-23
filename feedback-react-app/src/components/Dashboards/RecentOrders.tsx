import {
  Card,
  Grid,
  styled,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  SelectChangeEvent,
  FormControl, InputLabel, Select, MenuItem,
} from "@mui/material";
import { H5 } from "components/Typography";
import { FC, useEffect, useState } from "react";

import ScrollBar from "simplebar-react";

import api from "service/Api";
import toast from "react-hot-toast";
import { } from '@mui/material';
import {  } from '@mui/material';

const commonCSS = {
  minWidth: 120,
  "&:nth-of-type(2)": { minWidth: 170 },
  "&:nth-of-type(3)": { minWidth: 80 },
};

const HeadTableCell = styled(TableCell)(() => ({
  fontSize: 12,
  fontWeight: 600,
  "&:first-of-type": { paddingLeft: 0 },
  "&:last-of-type": { paddingRight: 0 },
}));

const BodyTableCell = styled(TableCell)(({ theme }) => ({
  fontSize: 12,
  fontWeight: 500,
  padding: 0,
  paddingLeft: "1rem",
  paddingTop: "0.7rem",
  "&:first-of-type": { paddingLeft: 0 },
  "&:last-of-type": { paddingRight: 0 },
  [theme.breakpoints.down("sm")]: { ...commonCSS },
  [theme.breakpoints.between(960, 1270)]: { ...commonCSS },
}));

interface Order {
  type: string;
  message: string;
  status: string;
}

const types = [{type:'ELOGIO', title:'Elogio'},
{type:'SUGESTAO', title:'Sugestão'},
 {type:'CRITICA',title:'Crítica'}
]

const RecentOrders: FC = () => {

  const [orderList, setOrderList] = useState<Order[]>([]);

  const [selectedValue, setSelectedValue] = useState('ELOGIO');

  const handleChange = (event: SelectChangeEvent<string>) => {
    setSelectedValue(event.target.value);
  };

  useEffect(() => {
    const loadOrderList = async () => {
      try {
        let query = `/feedbacks/todos/${selectedValue}`;
        const response = await api.get<Order[]>(query);
        setOrderList(response.data);
      } catch (error) {
        toast.error(`${error}`);
      }
    };
    loadOrderList();
  }, [selectedValue]);

  return (
    <Card sx={{ padding: "2rem"}}>
      
      <Grid container 
        spacing={{ xs: 4, sm: 4, md: 3 }}
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          padding: "2rem"
        }}
      >
        <H5>Feedbacks</H5>
        <FormControl sx={{ minWidth: 120 }}>
          <InputLabel id="select-label">Selecione um tipo</InputLabel>
          <Select
            labelId="select-label"
            id="custom-select"
            value={selectedValue}
            onChange={handleChange}
            label="Selecione um tipo"
            sx={{
              '& .MuiInputBase-root': {
                background: 'white', 
                borderRadius: '4px',
              },
              '& .MuiSelect-select': {
                padding: '10px',
              },
            }}
          >
            <MenuItem value={'ELOGIO'}> Elogio </MenuItem>
            <MenuItem value={'SUGESTAO'}> Sugestão </MenuItem>
            <MenuItem value={'CRITICA'}> Crítica </MenuItem>
          </Select>
        </FormControl>

      </Grid>
      <ScrollBar>
        <Table>
          <TableHead
            sx={{ borderBottom: "1.5px solid", borderColor: "divider" }}
          >
            <TableRow>
              <HeadTableCell sx={{ fontWeight: 'bold' }}> Tipo </HeadTableCell>
              <HeadTableCell sx={{ fontWeight: 'bold' }}> Descrição </HeadTableCell>
              <HeadTableCell sx={{ fontWeight: 'bold' }}> Status </HeadTableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {orderList.map((item: Order, index) => (
              <TableRow key={index}>
                <BodyTableCell>{types[types.map(i => i.type).indexOf(item.type)].title}</BodyTableCell>
                <BodyTableCell>{item.message}</BodyTableCell>
                <BodyTableCell>{item.status}</BodyTableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </ScrollBar>
    </Card>
  );
};

export default RecentOrders;
