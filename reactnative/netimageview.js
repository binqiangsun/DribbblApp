import { PropTypes } from 'react';
import { requireNativeComponent, View } from 'react-native';

var iface = {
  name: 'ImageView',
  propTypes: {
    imageUrl: PropTypes.string,
  },
};

module.exports = requireNativeComponent('NetImageView', iface);
