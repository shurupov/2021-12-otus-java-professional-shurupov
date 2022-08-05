import {connect} from "react-redux";
import {ApartmentList} from "components/ApartmentList/ApartmentList";

const mapToProps = (storeState: any) => {
    return {
        apartments: storeState.apartments.list
    }
}

export const ConnectedApartmentList = connect(mapToProps)(ApartmentList);