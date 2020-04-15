var appData = {
  heading: "键盘测试 Keyboard Test",
  text: "按下键盘上的按键检测功能是否正常。",
  keyboards: Keyboards,
  selectedKeyboard: {},
  isAppMode: false,
};
function getKeyboardLayout() {
  return appData.selectedKeyboard.layout;
}
function getKeyboardKeycode() {
  if (appData.isAppMode) {
    return appData.selectedKeyboard.app_keycode;
  } else {
    return appData.selectedKeyboard.web_keycode;
  }
}
function activeAppMode(){
  appData.isAppMode = true;
}


Vue.component('k-line', {
  props: {line : {Object}},
  data: function() {
    return {
      keyWidth: 50,
      styleObject: {
        marginTop: '0px',
      }
    }
  },
  methods: {
    setY: function(y){
      this.styleObject.marginTop = this.keyWidth * y + 'px';
    }
  },
  template: '<div class="k-line" v-bind:style="styleObject">' + 
  '<k-key v-for="(key, k) in line" v-bind:data="key" v-bind:pre-data="line[k-1]" ></k-key>' +
  ' </div>'
});

Vue.component('k-key', {
  props: {
    data: [String, Object],
    preData: [String, Object],
  },
  data: function() {
    return {
      isKey: true,
      keyname: this.data,
      keyWidth: 50,
      styleObject: {
        width: this.keyWidth + 'px',
        height: this.keyWidth + 'px',
        visibility: 'visible',
        display: 'inline',
      }
    }
  },
  created: function() {
    if (typeof(this.data) == 'object') {
      this.isKey = false;
      this.styleObject.visibility = 'hidden'; //'visible';
      if (this.data.x !== undefined) {
        this.styleObject.width = this.keyWidth * this.data.x + 'px !important';
      } else if (this.data.y !== undefined){
        this.styleObject.display = 'none';
        this.$parent.setY(this.data.y);
      } else {
        this.styleObject.display = 'none';
      }
    } else {
      this.isKey = true;
      this.styleObject.visibility = 'visible';

      if (typeof(this.preData) == 'object') {
        if (this.preData.w !== undefined) {
          this.styleObject.width = this.keyWidth * this.preData.w + 'px !important';
        }
        if (this.preData.h !== undefined) {
          this.styleObject.height = this.keyWidth * this.preData.h + 'px !important';
        }
      }
    }
  },
  computed: {
    isTrueKey: function() {
      return typeof(this.data) !== 'object';
    }
  },
  template: '<div class="k-key" v-bind:class="{key:isTrueKey}"  v-bind:style="styleObject">{{ keyname }}</div>',
});

var app = new Vue({
  el: "#vue-app",
  data: appData,
  methods: {
    chooseKeyboard: function(index){
      appData.keyboard = Keyboards.keycool84.layout;
      appData.keycode = Keyboards.keycool84.web_keycode;
    },
    clearState: function() {
      hold = [];
      $('.key').removeClass('pressed').removeClass('hold');
      $('#last_key').val('');
      $('#press_time').val('');
    },
    preKey: function(line, index) {
      return line[index - 1];
    },
  },
  created: function(){
    appData.selectedKeyboard = Keyboards.key87;
  },
});


var test_questions = ['同時按下方向鍵 上、下、左、右', '同時按下 Q, W, A, S', '同時按下 Z, X, C, V, U, I, O, P'];
var test_answers = [[37, 38, 39, 40], [65, 81, 83, 87], [67, 73, 79, 80, 85, 86, 88, 90]];
var test_index = 0;
var test_running = false;
var ghost = 0,
  absent = 0,
  usb_limit = 0;

var hold = [];
var tick, tock;

var NKRO_check = function(answer, input) {
  var i, index;
  var match = 0;

  for (i = 0; i < input.length; i++) {
    index = answer.indexOf(input[i]);
    if (index == -1) {
      ghost += 1;
    } else {
      match += 1;
    }
  }

  if (answer.length > 6 && input.length == 6 && match == 6) {
    usb_limit = +1;
  } else {
    absent += answer.length - match;
  }
};

var testKeyDown = function() {
  $('#progress_bar').stop().css({ width: '630px' }).animate({ width: 0 }, 3000, function() {
    var result = [];
    hold.sort();
    NKRO_check(test_answers[test_index], hold);
    test_index += 1;

    if (test_index < test_questions.length) {
      $('#question').text((test_index + 1) + ' / ' + test_questions.length + '：' + test_questions[test_index]);
      $('#progress_bar').css({ width: '630px' });
    } else {
      if (usb_limit != 0) {
        result.push('最多輸入 6 鍵');
      }
      if (absent != 0) {
        result.push('有按鍵衝突');
      }
      if (ghost != 0) {
        result.push('有 ghost key');
      }

      if (usb_limit == 0 && ghost == 0 && absent == 0) {
        result.push('正確處理 NKRO');
      } else {
        result.unshift('無法正確處理 NKRO');
      }

      $('#question').text('檢測結果：' + result.join('，') + '。');
      $('#progress_box').hide();
      $('#button_test').show();
      test_running = false;
    }
  });
};

$('#button_test').on('click', function() {
  test_running = true;
  test_index = 0;
  ghost = 0;
  absent = 0;
  usb_limit = 0;

  $('#progress_box').show();
  $('#button_test').hide();
  $('#question').show().text((test_index + 1) + ' / ' + test_questions.length + '：' + test_questions[test_index]);
  $('#progress_bar').css({ width: '630px' });
});

$('#progress_box').hide();
$('#question').hide();

var onKeyDown = function(evt, keycode) {
  var which = 0;
  if (evt){
    which = evt.which;
  }
  if (keycode){
    which = keycode
  }

  // Firefox uses different keycode from Chrome
  if (which == 59) {
    which = 186;
  } else if (which == 61) {
    which = 187;
  } else if (which == 173) {
    which = 189;
  }

  var keycode = getKeyboardKeycode();
  var i = keycode.indexOf(which);

  if (hold.length == 0) {
    tick = Date.now();
  }
  if (hold.indexOf(which) == -1) {
    hold.push(which);
    if (test_running) {
      testKeyDown();
    }
  }

  var elKey = $('.key:eq(' + i + ')');

  elKey.removeClass('pressed').addClass('hold');

  if (which === 93) {
    // manually release menu key
    elKey.removeClass('hold').addClass('pressed');
    hold.splice(hold.indexOf(i), 1);
  }

  var keyname = elKey.text();
  $('#last_key').val(keyname + ' (' + which + ')');
  $('#press_time').val('');

  if (evt) {  evt.preventDefault(); }
};

var onKeyUp = function(evt, keycode) {
  var which = 0;
  if (evt) {
    which = evt.which;
  }
  if (keycode) {
    which = keycode
  }
  
  // Firefox uses different keycode from Chrome
  if (which == 59) {
    which = 186;
  } else if (which == 61) {
    which = 187;
  } else if (which == 173) {
    which = 189;
  }

  var keycode = getKeyboardKeycode();
  var i = keycode.indexOf(which);

  tock = Date.now();
  hold.splice(hold.indexOf(which), 1);
  if (hold.length == 0) {
    $('#press_time').val((tock - tick) + ' ms');
  }

  var elKey = $('.key:eq(' + i + ')');
  if (which === 44) {
    // manually press printscreen key
    elKey.removeClass('pressed').addClass('hold');
  }

  elKey.removeClass('hold').addClass('pressed');
  
  if (evt) {  evt.preventDefault(); }
};


/*
  $('.key').each(function(index) {
    console.log(index);
    $(this).attr('id', 'k_' + index);//.text(default_keys[index]);
  });
*/
$(document).on('keydown', onKeyDown).on('keyup', onKeyUp);

function hello(){
  alert('hello world!');
}

function jsOnKeyDown(keyCode) {
  var which = parseInt(keyCode);
  //alert(which);
  onKeyDown(null, which);
}

function jsOnKeyUp(keyCode) {
  var which = parseInt(keyCode);
  onKeyUp(null, which);
}

