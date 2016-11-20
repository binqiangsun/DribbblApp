'use strict';
import Dimensions from 'Dimensions'
import {Platform} from 'react-native'
import NetImageView from './netimageview'
import React, {Component} from 'react';
import {
  Text,
  View,
  Image,
  AppRegistry,
  StyleSheet,
  ListView,
} from 'react-native';


const IS_ANDROID = Platform.OS == 'android'
export const WINDOW_HEIGHT = Dimensions.get('window').height - (IS_ANDROID ? 0 : 64)
export const WINDOW_WIDTH = Dimensions.get('window').width
export const Color = {
    LUKOU_RED: '#da372a'
}
export default IS_ANDROID


var REQUEST_URL = 'https://api.dribbble.com/v1/shots/';
var REQUEST_COMMENT_URL = 'https://api.dribbble.com/v1/shots/';
var ACCESS_TOKEN = '?access_token=acea171b23f1058c2a5de36300f708761f810513e8e083c349fc10ffabee4036';

class ShotInfo extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      shot: null,
      id:props.shotId,
      comments: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2,
            }),
    };
    this._list = [];
  }

  render() {
    if(this._list.length == 0) {
      return this.renderLoadingView();
    }
    return this.renderShot()
  }

  renderLoadingView(){
    return(
      <View style={styles.container}>
        <Text style={styles.content}> Loading shot: { this.state.id }</Text>
      </View>
      )
  }

  renderShot(){
        return (
          <View style={ styles.container }>
            <ListView
              dataSource={ this.state.comments }
                    renderRow={ this.renderComment.bind(this) }
            />
          </View>
        )
  }

  renderComment(comment){
    alert(comment.body);
    return (
        <View>
            <Text >{comment.body}</Text>
        </View>
    )
  }

  renderHead(){
    return (
        <View>
            <NetImageView imageUrl={shot.images.hidpi} style={ styles.shotImg } />
            <Text style={ styles.shotTitle }>{ shot.title }</Text>
            <Text style={ styles.shotText }>{shot.description}</Text>
        </View>
    )
  }

  componentDidMount() {
    this.fetchShot();
    this.fetchComments();
  }


  fetchShot(){
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

    fetchComments(){
        var request_url = REQUEST_URL + this.state.id + "/comments" + ACCESS_TOKEN;
        fetch(request_url)
        .then((response) => response.json())
        .then((responseData) => {
            alert(responseData.length)
            this.getDataSource(responseData)
          })
        .done();
    }

    getDataSource(list) {
        this._list = this._list.concat(list)
        return this.state.comments.cloneWithRows(this._list)
    }
}



var styles = StyleSheet.create({
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

AppRegistry.registerComponent('ShotInfo', () => ShotInfo);
