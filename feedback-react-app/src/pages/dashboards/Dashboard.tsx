import { FC } from "react";

import { Box, Grid, useTheme } from "@mui/material";

import useTitle from "hooks/useTitle";
import EarningIcon from "icons/EarningIcon";

import Analytics from "components/Dashboards/Analytics";
import DashboardCard from "components/Dashboards/Card";
import RecentOrders from "components/Dashboards/RecentOrders";

const Dashboard: FC = () => {
  useTitle("Dashboard");

  const theme = useTheme();

  const cardList = [
    {
      total: 2,
      Icon: EarningIcon,
      title: "Sugestões",
      color: theme.palette.primary.main,
    },
    {
      total: 1,
      title: "Elogios",
      Icon: EarningIcon,
      color: theme.palette.primary.green,
    },
    {
      total: 1,
      Icon: EarningIcon,
      title: "Críticas",
      color: theme.palette.primary.red,
    },
  ];

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
