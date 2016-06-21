'use strict';
import Dimensions from 'Dimensions'
import {Platform} from 'react-native'

import React, {
  Text,
  View,
  Image
} from 'react-native';


const IS_ANDROID = Platform.OS == 'android'
export const WINDOW_HEIGHT = Dimensions.get('window').height - (IS_ANDROID ? 0 : 64)
export const WINDOW_WIDTH = Dimensions.get('window').width
export const Color = {
    LUKOU_RED: '#da372a'
}
export default IS_ANDROID


var REQUEST_URL = 'https://api.dribbble.com/v1/shots/';
var ACCESS_TOKEN = '?access_token=acea171b23f1058c2a5de36300f708761f810513e8e083c349fc10ffabee4036';

class ShotInfo extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      shot: null,
      id:props.shotId,
    };
  }

  render() {
    if(!this.state.shot) {
      return this.renderLoadingView();
    }
    var shot = this.state.shot;
    return this.renderShot(shot)
  }

  renderLoadingView(){
    return(
      <View style={styles.container}>
        <Text style={styles.content}> Loading shot: { this.state.id }</Text>
      </View>
      )
  }

  renderShot(shot){
    return (
      <View style={ styles.container }>
        <Image source={{ uri: shot.images.hidpi }} style={ styles.shotImg } />
        <Text style={ styles.shotTitle }>{ shot.title }</Text>
        <Text style={ styles.shotText }>{shot.description}</Text>
      </View>
    )
  }

  componentDidMount() {
    this.fetchData();
  }


  fetchData(){
    var request_url = REQUEST_URL + this.state.id + ACCESS_TOKEN;
    fetch(request_url)
    .then((response) => response.json())
    .then((responseData) => {
        this.setState({
          shot: responseData,
        });
      })
    .done();
  }
}



var styles = React.StyleSheet.create({
  container: {
    flex: 1,
  },
  content: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  shotImg: {
    width: WINDOW_WIDTH ,
    height: WINDOW_WIDTH * 0.75,
    resizeMode: 'stretch',
    justifyContent: 'center',
    padding: 10,
  },
  shotTitle: {
      fontSize: 18,
      padding: 10,
  },
  shotText: {
      fontSize: 14,
      padding: 16,
  },
});

React.AppRegistry.registerComponent('ShotInfo', () => ShotInfo);