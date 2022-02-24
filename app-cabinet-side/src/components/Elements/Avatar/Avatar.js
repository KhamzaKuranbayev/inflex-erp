import st from "classnames";
import "./Avatar.scss";

export const Avatar = ({
  url = "https://www.w3schools.com/w3css/img_avatar3.png",
  circle,
  bordered,
  sm,
  md,
  lg,
  xl,
  ...rest
}) => {
  return (
    <div
      className={st("avatar__wrapper", {
        circle,
        bordered,
        sm,
        md,
        lg,
        xl,
      })}
      {...rest}
    >
      <img src={url} alt="userLogo" className="img-fluid" />
    </div>
  );
};
