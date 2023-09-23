import { FC, useEffect, useState } from "react";

import { Box, Card, useTheme } from "@mui/material";
import { ApexOptions } from "apexcharts";
import FlexBox from "components/FlexBox";
import { H5 } from "components/Typography";
import Chart from "react-apexcharts";

import api from 'service/Api';
import toast from "react-hot-toast";



const Analytics: FC = () => {
  const theme = useTheme();

  const [data, setData] = useState({
    series: [0, 0, 0],
    categories: ["Sugestão", "Elogio", "Critica"],
  });
  
  const types = [{type:'ELOGIO', title:'Elogio', total:0},
  {type:'SUGESTAO', title:'Sugestão',total:0},
   {type:'CRITICA',title:'Critica',total:0}]
  
  useEffect(() => {
    const loadCardList = async () => {
      let  totalGeral = 0
        for (const type of types) {
          try {
            let query = `/feedbacks/tamanho-fila/${type.type}`
            const response = await api.get(query);
            type.total = Number(response.data);
          } catch (error) {
            toast.error(`${error}`);
          }
      }

      totalGeral =types.reduce((total, item) => total + item.total, 0);
      console.log(totalGeral)

      const updatedSeries = data.categories.map((category, indice) => {
        const objetoEncontrado = types.find(item => item.title === category);
        return objetoEncontrado ? parseFloat(((objetoEncontrado.total / totalGeral) * 100).toFixed(2)) : 0;
      });

      setData({ ...data, series: updatedSeries });

      console.log(data)
    };

    
    loadCardList();

  }, []);



  const chartOptions: ApexOptions = {
    chart: { background: "transparent" },
    colors: [
      theme.palette.primary.main,
      theme.palette.primary.green,
      theme.palette.primary.red,
    ],
    labels: ["Sugestão", "Elogio", "Critica"],
    plotOptions: {
      radialBar: {
        dataLabels: {
          name: { show: false },
          value: { show: false },
        },
        hollow: { size: "28%" },
        track: {
          background: theme.palette.divider,
          margin: 12,
        },
      },
    },
    theme: {
      mode: theme.palette.mode,
    },
    stroke: {
      lineCap: "round",
      curve: "smooth",
    },
    legend: {
      show: true,
      position: "bottom",
      fontFamily: "inherit",
      fontSize: "13px",
      fontWeight: 500,
      onItemClick: { toggleDataSeries: false },
      onItemHover: { highlightDataSeries: true },
    },
    tooltip: {
      enabled: true,
      style: { fontFamily: "inherit" },
      y: {
        formatter: (value) => `$${value}`,
      },
    },
    states: {
      hover: {
        filter: { type: "none" },
      },
      active: {
        filter: { type: "none" },
      },
    },
  };

  const chartSeries = data.series;
  return (
    <Card
      sx={{
        padding: "2rem",
        height: "95%",
        [theme.breakpoints.down(425)]: { padding: "1.5rem" },
      }}
    >
      <FlexBox alignItems="center" justifyContent="space-between">
        <H5>Analytics</H5>
      </FlexBox>

      <Box
        sx={{
          paddingTop: 2,
          "& .apexcharts-tooltip": {
            boxShadow: "none",
            "& .apexcharts-active": { paddingBottom: 0 },
            "&.apexcharts-theme-light": {
              border: "none",
              color: "white",
              borderRadius: "8px",
            },
          },
          "& .apexcharts-legend.position-bottom.apexcharts-align-center, .apexcharts-legend.position-top.apexcharts-align-center":
            { justifyContent: "space-evenly" },
          [theme.breakpoints.down(425)]: { padding: 0 },
        }}
      >
        <Chart
          options={chartOptions}
          series={chartSeries}
          type="pie"
          height={300}
        />
      </Box>
    </Card>
  );
};

export default Analytics;
