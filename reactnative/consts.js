import Dimensions from 'Dimensions'
import {Platform} from 'react-native'

const IS_ANDROID = Platform.OS == 'android'

export const WINDOW_HEIGHT = Dimensions.get('window').height - (IS_ANDROID ? 0 : 64)
export const WINDOW_WIDTH = Dimensions.get('window').width

export const Color = {
    LUKOU_RED: '#da372a'
}

export default IS_ANDROID
