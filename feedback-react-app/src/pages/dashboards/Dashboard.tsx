import { FC, useEffect, useState } from "react";

import { Box, Grid, useTheme } from "@mui/material";

import useTitle from "hooks/useTitle";
import EarningIcon from "icons/EarningIcon";

import Analytics from "components/Dashboards/Analytics";
import DashboardCard from "components/Dashboards/Card";
import RecentOrders from "components/Dashboards/RecentOrders";

import api from 'service/Api';
import toast from "react-hot-toast";

const Dashboard: FC = () => {
  useTitle("Dashboard");

  const theme = useTheme();

  const types = [{type:'ELOGIO', title:'Elogio', total:0,  Icon: EarningIcon, color:theme.palette.primary.green},
  {type:'SUGESTAO', title:'Sugestão',total:0,  Icon: EarningIcon, color:theme.palette.primary.main},
   {type:'CRITICA',title:'Crítica',total:0,  Icon: EarningIcon, color:theme.palette.primary.red}]
  
   const [cardList, setCardList] = useState<unknown[]>([]);

  useEffect(() => {
    const loadCardList = async () => {
        for (const type of types) {
          try {
            let query = `/feedbacks/tamanho-fila/${type.type}`
            const response = await api.get(query);
            type.total = Number(response.data);
          } catch (error) {
            toast.error(`${error}`);
          }
      }
      setCardList(types)
    };

    
    loadCardList();
  }, []);



  return (
    <Box pt={2} pb={4}>
      <Grid container spacing={{ xs: 4, sm: 4, md: 3 }}>
        {cardList.map((card, index) => (
          <Grid item lg={4} md={4} xs={12} key={index}>
            <DashboardCard card={card} />
          </Grid>
        ))}
      </Grid>

      <Grid container spacing={4} pt={4}>
        <Grid item lg={8} md={8} xs={12}>
          <RecentOrders />
        </Grid>

        <Grid item lg={4} md={4} xs={12}>
          <Analytics />
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;
