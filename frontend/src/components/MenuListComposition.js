import React, {Component} from "react";
import MenuItem from "@material-ui/core/es/MenuItem/MenuItem";
import MenuIcon from '@material-ui/icons/Menu';
import Menu from "@material-ui/core/Menu/Menu";
import PopupState, { bindTrigger, bindMenu } from 'material-ui-popup-state/index';
import {connect} from "react-redux";
import {Link as RouterLink} from "react-router-dom";

class MenuListComposition extends Component {
    render() {
        const { loggedIn } = this.props.auth;
        if (!loggedIn) {
            return (
                <MenuIcon/>
            )
        }
        return (
            <div>
                <PopupState variant="popover" popupId="demo-popup-menu">
                    {popupState => (
                        <React.Fragment>
                            <MenuIcon variant="contained" {...bindTrigger(popupState)}/>
                            <Menu {...bindMenu(popupState)}>
                                <MenuItem component={RouterLink} to='/showPlaces' onClick={popupState.close}>
                                    Miejsca
                                </MenuItem>
                                <MenuItem component={RouterLink} to='/showSpeakers' onClick={popupState.close}>
                                    Prelegenci
                                </MenuItem>
                                <MenuItem component={RouterLink} to='/' onClick={popupState.close}>
                                    Kalendarz
                                </MenuItem>
                                <MenuItem component={RouterLink} to='/users' onClick={popupState.close}>
                                    Użytkownicy
                                </MenuItem>
                            </Menu>
                        </React.Fragment>
                    )}
                </PopupState>
            </div>
        )
    }

}
const mapStateToProps = state => ({
    auth: state.authentication
});
export default connect(mapStateToProps)(MenuListComposition);
