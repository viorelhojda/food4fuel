function genericChartSkin() {
    this.cfg.seriesColors = ['#27aae1', '#F08080', '#dae8ef', '#DDA0DD', '#FFE4E1', '#33ccff', '#FFA07A', '#9fadb5', '#FF7F50', '#FFFACD', '#DEB887', '#8FBC8F', '#AFEEEE'];
    this.cfg.grid = {
            background: '#ffffff',
            borderColor: '#eaeaea',
            gridLineColor: '#e5ebf0',
            shadow: false,
            borderWidth: 0
    };
    this.cfg.seriesDefaults.shadow = false;
}

function lineChartSkin() {
    this.cfg.shadow = false;
    this.cfg.seriesColors = ['#27aae1', '#F08080'];
    this.cfg.grid = {
            background: '#f3f5f7',
            borderColor: '#e5ebf0',
            gridLineColor: '#e5ebf0',
            shadow: false
    };
    this.cfg.axesDefaults = {
            rendererOptions: {
                textColor: '#9fadb5'
            }
    };
    this.cfg.seriesDefaults = {
            shadow: false,
            markerOptions: {
                shadow: false
            }
    };
}