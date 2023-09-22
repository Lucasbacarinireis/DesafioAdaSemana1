import {
  Card,
  styled,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@mui/material";
import { H5 } from "components/Typography";
import { FC } from "react";
import ScrollBar from "simplebar-react";

const commonCSS = {
  minWidth: 120,
  "&:nth-of-type(2)": { minWidth: 170 },
  "&:nth-of-type(3)": { minWidth: 80 },
};

// Styled components
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

const orderList = [
  {
    type: "Sugestão",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras nec ipsum a lorem aliquam rutrum nec nec erat.",
    status: "Recebido",
  },
  {
    type: "Elogio",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras nec ipsum a lorem aliquam rutrum nec nec erat.",
    status: "Recebido",
  },
  {
    type: "Crítica",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras nec ipsum a lorem aliquam rutrum nec nec erat.",
    status: "Recebido",
  },
  {
    type: "Sugestão",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras nec ipsum a lorem aliquam rutrum nec nec erat.",
    status: "Recebido",
  },
];
const RecentOrders: FC = () => {
  return (
    <Card sx={{ padding: "2rem" }}>
      <H5>Feedbacks recentes</H5>

      <ScrollBar>
        <Table>
          <TableHead
            sx={{ borderBottom: "1.5px solid", borderColor: "divider" }}
          >
            <TableRow>
              <HeadTableCell> Tipo </HeadTableCell>
              <HeadTableCell> Descrição </HeadTableCell>
              <HeadTableCell> Status </HeadTableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {orderList.map((item, index) => (
              <TableRow key={index}>
                <BodyTableCell>{item.type}</BodyTableCell>
                <BodyTableCell>{item.description}</BodyTableCell>
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
