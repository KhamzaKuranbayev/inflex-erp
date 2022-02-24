import st from "classnames";
import "./Select.scss";

export const Select = ({ list = [], ...rest }) => {
  return (
    <select {...rest}>
      {list.map((i) => (
        <option key={i.id} value={i.value}>
          {i.title}
        </option>
      ))}
    </select>
  );
};
