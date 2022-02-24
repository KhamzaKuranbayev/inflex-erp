import { useState } from "react";
import { Input } from "../../components/Elements/Input/Input";
import { Select } from "../../components/Elements/Select/Select";
import { Navbar } from "../../components/Navbar/Navbar";
import { Sidebar } from "../../components/Sidebar/Sidebar";
import cl from "./Dashboard.module.scss";

export const Dashboard = ({ children }) => {
  const [isOpen, SetIsOpen] = useState(false);

  const toggleOpen = () => {
    SetIsOpen((p) => !p);
  };

  return (
    <div className={cl.dashboard__cabinet}>
      <Navbar onToggleOpen={toggleOpen} isOpen={isOpen} />
      <div
        className={
          cl.dashboard__sidebar + " " + (isOpen ? cl.dashboard__open : "")
        }
      >
        <Sidebar onToggleOpen={toggleOpen} />
        <main onClick={toggleOpen} />
      </div>
      <div className={cl.dashboard__children}>
        <div className={cl.dashboard__children__content}>
          {children}
          {/* <Select
            list={[
              { id: 1, title: "Asad", value: "asad" },
              { id: 2, title: "Avva", value: "avva" },
              { id: 3, title: "Dagr", value: "dagr" },
            ]}
          /> */}
          <Input
            type="text"
            size="lg"
            label="Email"
            error=""
            required
          />
        </div>
      </div>
    </div>
  );
};
